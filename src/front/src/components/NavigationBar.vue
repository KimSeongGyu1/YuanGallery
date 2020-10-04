<template>
    <div>
        <v-app-bar id="navigation-bar">
            <v-toolbar-title>
                <v-btn
                    @click="goToHome"
                    text
                    x-large
                    id="homeButton"
                >
                    유안의 저장소
                </v-btn>
            </v-toolbar-title>
            <v-spacer/>
            <div id="search_box">
                <v-text-field
                        label="Search"
                        rounded
                        color="white"
                        background-color="white"
                        hide-details
                        v-model="searchWord"
                        @keyup.enter="searchByInput"
                ></v-text-field>
            </div>
        </v-app-bar>
    </div>
</template>

<script>
export default {
    data() {
      return {
          searchWord: ""
      }
    },
    methods: {
        goToHome(event) {
            event.preventDefault();
            this.searchWord = "";
            this.$router.push('/');
        },
        searchByInput(event) {
            event.preventDefault();
            if (this.searchWord) {
                this.$router.push(`/search?title=${this.searchWord}`)
            }
        }
    }
}
</script>

<style scoped>
#navigation-bar {
    background-color: #9FD0D4;
}
#homeButton {
    font-size: 22px;
    font-weight: bold;
    color: #f4f4f4;
}
#search_box {
    max-width: 250px;
    font-weight: normal;
}
</style>