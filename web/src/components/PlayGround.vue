<template>
    <div class="playground">
        <GameMap/>
    </div>
</template>

<script>
import GameMap from '@/components/GameMap.vue'

export default{
    components: {
        GameMap,
    }
}
</script>

<style scoped>
.playground{
    width: 70vw;
    height: 60vh;
    /* background-color: lightblue; */
    margin: 40px auto;
}
</style>