import axios from "axios";

function getAction(url) {
    return axios.get(url);
}

function postAction(url, request) {
    return axios.post(url, request);
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