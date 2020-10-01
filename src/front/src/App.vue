<template>
  <v-app>
    <NavigationBar/>
  </v-app>
</template>

<script>
import NavigationBar from './components/NavigationBar';

export default {
  name: 'App',

  components: {
    NavigationBar
  },

  data: () => ({
    //
  }),
};
</script>
