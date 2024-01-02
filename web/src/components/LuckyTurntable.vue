<template>
    <LuckyWheel
            ref="myLucky"
            width="300px"
            height="300px"
            :prizes="prizes"
            :blocks="blocks"
            :buttons="buttons"
            @start="startCallback"
            @end="endCallback"
    />
</template>

<script>
// import $ from 'jquery';
// import {useStore} from "vuex";

    export default {
        data() {
            return {
                blocks: [{padding: '13px', background: '#617df2'}],
                prizes: [
                    // {fonts: [{text: '0', top: '10%'}], background: '#e9e8fe'},
                    // {fonts: [{text: '1', top: '10%'}], background: '#b8c5f2'},
                    // {fonts: [{text: '2', top: '10%'}], background: '#e9e8fe'},
                    // {fonts: [{text: '3', top: '10%'}], background: '#b8c5f2'},
                    // {fonts: [{text: '4', top: '10%'}], background: '#e9e8fe'},
                    // {fonts: [{text: '5', top: '10%'}], background: '#b8c5f2'},
                ],
                buttons: [
                    {radius: '50px', background: '#617df2'},
                    {radius: '45px', background: '#afc8ff'},
                    {
                        radius: '40px', background: '#869cfa',
                        pointer: true,
                        fonts: [{text: '开始\n抽奖', top: '-20px'}]
                    },
                ],
            }
        },
        created() {
            // 初始化奖品数据
            this.initPrizeList();
        },
        methods: {
            initPrizeList() {
              // 获取抽奖列表数据
              let resp = getJsonSynchronously('http://localhost:9001/api/queryPrizeList');
              this.prizes = [...resp];

              function getJsonSynchronously(url) {
                let xhr = new XMLHttpRequest();
                // 第三个参数设置为false，使请求为同步
                xhr.open('GET', url, false);
                xhr.setRequestHeader('Accept', 'application/json');
                try {
                  xhr.send();

                  if (xhr.status === 200) {
                    return JSON.parse(xhr.responseText);
                  } else {
                    throw new Error('Request failed with status ' + xhr.status);
                  }
                } catch (e) {
                  console.error('An error occurred:', e);
                  return null;
                }
              }
            },
            // 点击抽奖按钮会触发star回调
            startCallback() {
                // 调用抽奖组件的play方法开始游戏
                this.$refs.myLucky.play()
                // 模拟调用接口异步抽奖
                setTimeout(() => {
                    // 假设后端返回的中奖索引是0
                    const index = 0
                    // 调用stop停止旋转并传递中奖索引
                    this.$refs.myLucky.stop(index)
                }, 3000)
            },
            // 抽奖结束会触发end回调
            endCallback(prize) {
                console.log(prize.fonts)
            },
        }
    }
</script>