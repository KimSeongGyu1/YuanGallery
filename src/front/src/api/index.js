import axios from "axios";

const yuanToken =`yuanToken ${getCookie("yuanToken")}`;

function getCookie(name) {
    const cookies = document.cookie.split("; ");
    const value = cookies.find(cookie => {
        return cookie.startsWith(name);
    })
    if (value) {
        return value.split("=")[1];
    }
    return "";
}

function getAction(url) {
    return axios.get(url, {
        headers: {
            Authorization: yuanToken
        }
    });
}

function postAction(url, request) {
    return axios.post(url, request, {
        headers: {
            Authorization: yuanToken
        }
    });
}

function putAction(url) {
    return axios.put(url);
}

function patchAction(url) {
    return axios.patch(url);
}

function deleteAction(url) {
    return axios.delete(url);
}

export {
    getAction,
    postAction,
    putAction,
    patchAction,
    deleteAction
}