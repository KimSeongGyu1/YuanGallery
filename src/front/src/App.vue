<template>
  <v-app>
    <NavigationBar/>
    <router-view/>
    <Footer/>
  </v-app>
</template>

<script>
import NavigationBar from './components/NavigationBar';
import Footer from "./components/Footer";

export default {
  name: 'App',

  components: {
    NavigationBar,
    Footer
  },

  data: () => ({
    //
  }),
};
</script>
