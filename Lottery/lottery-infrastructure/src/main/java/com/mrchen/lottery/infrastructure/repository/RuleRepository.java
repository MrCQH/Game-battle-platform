package com.mrchen.lottery.infrastructure.repository;

import com.mrchen.lottery.common.Constants;
import com.mrchen.lottery.domain.rule.model.aggregate.TreeRuleRich;
import com.mrchen.lottery.domain.rule.model.vo.TreeNodeLineVO;
import com.mrchen.lottery.domain.rule.model.vo.TreeNodeVO;
import com.mrchen.lottery.domain.rule.model.vo.TreeRootVO;
import com.mrchen.lottery.domain.rule.repository.IRuleRepository;
import com.mrchen.lottery.infrastructure.dao.RuleTreeDao;
import com.mrchen.lottery.infrastructure.dao.RuleTreeNodeDao;
import com.mrchen.lottery.infrastructure.dao.RuleTreeNodeLineDao;
import com.mrchen.lottery.infrastructure.po.RuleTree;
import com.mrchen.lottery.infrastructure.po.RuleTreeNode;
import com.mrchen.lottery.infrastructure.po.RuleTreeNodeLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 规则信息仓储服务
 * @author：cqh
 * @date: 2023/6/21
 */
@Repository
public class RuleRepository implements IRuleRepository {
    @Resource
    private RuleTreeDao ruleTreeDao;

    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;

    @Override
    public TreeRuleRich queryRuleTreeRich(Long treeId) {
        // 存root
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        TreeRootVO treeRoot = new TreeRootVO();
        treeRoot.setTreeId(ruleTree.getId());
        treeRoot.setTreeRootId(ruleTree.getTreeRootNodeId());
        treeRoot.setTreeName(ruleTree.getTreeName());

        // 树节点 -> 树的边
        Map<Long, TreeNodeVO> treeNodeMap = new HashMap<>();
        // 查询出这个树的所有点
        List<RuleTreeNode> ruleTreeNodeList = ruleTreeNodeDao.queryRuleTreeNodeList(treeId);
        for (RuleTreeNode treeNode : ruleTreeNodeList){
            List<TreeNodeLineVO> treeNodeLineInfoList = new ArrayList<>();
            // 如果不是叶子节点
            if (Constants.NodeType.STEM.equals(treeNode.getNodeType())) {
                RuleTreeNodeLine ruleTreeNodeLineReq = new RuleTreeNodeLine();
                ruleTreeNodeLineReq.setTreeId(treeId);
                ruleTreeNodeLineReq.setNodeIdFrom(treeNode.getId());
                // 找到连接该点的所有的边
                List<RuleTreeNodeLine> ruleTreeNodeLineList =
                        ruleTreeNodeLineDao.queryRuleTreeNodeLineList(ruleTreeNodeLineReq);

                for (RuleTreeNodeLine nodeLine : ruleTreeNodeLineList){
                    // 封装 treeNodeLineInfo
                    TreeNodeLineVO treeNodeLineInfo = new TreeNodeLineVO();
                    treeNodeLineInfo.setNodeIdFrom(nodeLine.getNodeIdFrom());
                    treeNodeLineInfo.setNodeIdTo(nodeLine.getNodeIdTo());
                    treeNodeLineInfo.setRuleLimitType(nodeLine.getRuleLimitType());
                    treeNodeLineInfo.setRuleLimitValue(nodeLine.getRuleLimitValue());
                    treeNodeLineInfoList.add(treeNodeLineInfo);
                }
            }

            // 封装 treeNodeInfo 该点以及连接该点的所有边
            TreeNodeVO treeNodeInfo = new TreeNodeVO();
            treeNodeInfo.setTreeId(treeNode.getTreeId());
            treeNodeInfo.setTreeNodeId(treeNode.getId());
            treeNodeInfo.setNodeType(treeNode.getNodeType());
            treeNodeInfo.setNodeValue(treeNode.getNodeValue());
            treeNodeInfo.setRuleKey(treeNode.getRuleKey());
            treeNodeInfo.setRuleDesc(treeNode.getRuleDesc());
            treeNodeInfo.setTreeNodeLineInfoList(treeNodeLineInfoList);

            treeNodeMap.put(treeNode.getId(), treeNodeInfo);
        }

        TreeRuleRich treeRuleRich = new TreeRuleRich();
        treeRuleRich.setTreeRoot(treeRoot);
        treeRuleRich.setTreeNodeMap(treeNodeMap);
        return treeRuleRich;
    }
}
