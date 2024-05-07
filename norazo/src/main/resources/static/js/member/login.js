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
};
// 이메일, 비밀번호 미작성 시 로그인 막기 
const loginForm = document.querySelector("#loginForm");

const loginPw = document.querySelector("#loginForm input[name='memberPw']");

if(loginForm != null){

    loginForm.addEventListener("submit", e =>{

        if(loginEmail.value.trim().length === 0){
            alert("이메일을 작성해주세요!");
            e.preventDefault();
            loginEmail.focus();
            return;
        }
        if(loginPw.value.trim().length === 0){
            alert("비밀번호를 작성해주세요!");
            e.preventDefault();
            loginPw.focus();
            return;
        }

    });
}