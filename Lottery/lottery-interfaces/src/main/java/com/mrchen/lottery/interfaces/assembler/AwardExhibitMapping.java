package com.mrchen.lottery.interfaces.assembler;

import com.mrchen.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.mrchen.lottery.rpc.award.exhibit.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardExhibitMapping extends IMapping<DrawAwardVO, AwardDTO>{


    @Mapping(target = "userId", source = "uId")
    @Override
    AwardDTO sourceToTarget(DrawAwardVO var1);

    @Override
    List<AwardDTO> sourceToTarget(List<DrawAwardVO> var1);


}

