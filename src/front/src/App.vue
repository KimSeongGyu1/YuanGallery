<template>
  <v-app>
    <NavigationBar/>
    <PostList/>
  </v-app>
</template>

<script>
import NavigationBar from './components/NavigationBar';
import PostList from "./components/PostList";

export default {
  name: 'App',

  components: {
    NavigationBar,
    PostList
  },

  data: () => ({
    //
  }),
};
</script>
