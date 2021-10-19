<template>
  <div>
    <div>
      <dropdown-menu></dropdown-menu>
    </div>
    <div class="content">
      <router-view/>
    </div>
  </div>
</template>

<script>
import DropdownMenu from "./components/DropdownMenu";

export default {
  components: DropdownMenu,
  name: 'App',
}
</script>

<style>
.content {
  padding: 3% 10% 0;
}
</style>