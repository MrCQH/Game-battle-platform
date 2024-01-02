<template>
    <div class="container">
        <div class="card content-flied">
            <div class="card-body">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script>
export default{
    name: 'ContentView',
}
</script>

<style scoped>
.content-flied{
    margin-top: 1rem;
}
</style>