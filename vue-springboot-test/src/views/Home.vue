<template>
     <div>
        <img alt="Vue logo" src="@/assets/logo.png">
        <HelloWorld class="demo" msg="Welcome to Your Vue.js App"/>
        <div>路径 /app1</div>
     </div>
</template>
<script>
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>
