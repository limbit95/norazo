/* 쿠키 */
const getCookie= (key) => {

    const cookies = document.cookie;

    const cookieList =cookies.split("; ")
    .map( el => el.split("="));

    const obj = {};

    for(let i=0; i<cookieList.length; i++){
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k] = v;
    }

    return obj[key];
}

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");

if(loginEmail != null){
    
    const saveId = getCookie("saveId");

    if(saveId != undefined){
        loginEmail.value = saveId;

        document.querySelector("input[name='saveId']").checked = true;
    }
}