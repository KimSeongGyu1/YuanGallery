import axios from "axios";

async function getCookie(name) {
    const cookies = await document.cookie.split("; ");
    const value = await cookies.find(cookie => {
        return cookie.startsWith(name);
    })
    if (value) {
        return value.split("=")[1];
    } else {
        return "";
    }
}

async function getAction(url) {
    const tokenValue = await getCookie("yuanToken");
    const yuanToken = `yuanToken ${tokenValue}`;

    return await axios.get(url, {
        headers: {
            Authorization: yuanToken
        }
    });
}

async function postAction(url, request) {
    const tokenValue = await getCookie("yuanToken");
    const yuanToken = `yuanToken ${tokenValue}`;

    return await axios.post(url, request, {
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