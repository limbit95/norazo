// 메일이 있는지 검사  
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");


const checkObj = {
    "memberEmail" : false,
    "authKey" : false
}
memberEmail.addEventListener("input", e => {

    const inputEmail = e.target.value;
    
    if( inputEmail.trim().length === 0){
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요";

        emailMessage.classList.remove("confirm","error");
        checkObj.memberEmail = false;

        memberEmail.value = "";

        return;
    }
    
    fetch("/member/checkEmail?memberEmail="+ inputEmail )
    .then( resp => resp.text() )
    .then( count => {
        
        if(count == 0){
            emailMessage.innerText = "가입되지 않은 이메일 입니다.";
            emailMessage.classList.add("error");
            emailMessage.classList.remove("confirm");
            checkObj.memberEmail = false;
            return;
        }

        emailMessage.innerText = "";
        emailMessage.classList.remove("error");
        checkObj.memberEmail = true;
    })
    .catch(error =>{
        console.log(error);
    });
});

// ----------------------------------------------------------------------------
// 인증번호 받기 
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");
const authKey = document.querySelector("#authKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");
const authKeyMessage = document.querySelector("#authKeyMessage");

// 5분 타이머 역활 
let authTimer;

const initMin = 4;
const initSec = 59;
const initTime = "05:00";

let min = initMin;
let sec = initSec;

// 인증번호 받기 클릭 시 
sendAuthKeyBtn.addEventListener("click", () => {

  checkObj.authKey = false;
  authKeyMessage.innerText = "";

  if(!checkObj.memberEmail){
    alert("유효한 이메일 작성 후 클릭해 주세요,");
    return;
  }
  
  min = initMin;
  sec = initSec;

  clearInterval(authTimer);
  // ---------------------------------------------------
  // 비동기 메일보내기 
  fetch("/email/signUp",{
    method : "post",
    headers : {"Content-Type" : "application/json"},
    body : memberEmail.value
  })
  .then(resp => resp.text())
  .then(result => {
    if(result == 1){
      console.log("인증번호 발송 성공");

    } else{
      console.log("인증번호 발송 실패!");
    }

  });
  // ---------------------------------------------------
  authKeyMessage.innerText = initTime;
  authKeyMessage.classList.remove("confirm","error");

  alert("인증번호가 발송 되었습니다.");

  authTimer = setInterval( () => {

    authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    if(min == 0 && sec == 0){
      checkObj.authKey = false;
      clearInterval(authTimer);
      authKeyMessage.classList.add('error');
      authKeyMessage.classList.remove('confirm');
      return;
    }

    if(sec == 0){
      sec = 60;
      min --;
    }
    sec --;
  },1000);
});

function addZero(number){
  if(number < 10) return "0" + number;
  else return number;
}
// -------------------------------------------------------------------

// 인증번호 버튼 클릭 시  비동기로 서버 전달 
checkAuthKeyBtn.addEventListener("click", ()=> {

  if( min === 0 && sec === 0){
    
    alert("인증번호 입력 제한 시간을 초과하였습니다! ");
    return;

  }

  if(authKey.value.length < 6){
    
    alert("인증번호를 정확히 입력해주세요");
    return;
  
  }
  const obj = {
    "email" : memberEmail.value,
    "authKey" : authKey.value
  };

  fetch("/email/checkAuthKey", {
    method : "post",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(resp => resp.text())
  .then(result => {
    
    if(result == 0){
      alert("인증번호가 일치하지 않습니다");
      checkObj.authKey = false;
      return;
    }

    clearInterval(authTimer);
    authKeyMessage.innerText = "인증 되었습니다."
    authKeyMessage.classList.remove("error");
    authKeyMessage.classList.add("confirm");

    checkObj.authKey = true;

  });

});

console.log(checkObj);

const findPwBtn = document.querySelector("#findPwBtn");

findPwBtn.addEventListener("click", e =>{
    for(let key in checkObj){

        if( !checkObj[key] ){
  
          let str; 
  
          switch(key){
            case "memberEmail" : str = "이메일이 유효하지 않습니다"; break;
            case "authKey" : str = "이메일이 인증되지 않았습니다."; break;
           
          }
          alert(str);
  
          document.getElementById(key).focus();
          e.preventDefault();
          return;
        }
      }
      fetch("/email/findPw", {
        method: "post",
        headers: { "Content-Type": "application/json" },
        body : memberEmail.value 
    })
    .then(resp => resp.text())
    .then(count => {
        if (count == 1) {
            console.log("인증번호 발송 성공");
        } else {
            console.log("인증번호 발송 실패!");
        }
    })
    .catch(error => {
        console.error("인증번호 발송 중 오류 발생:", error);
    });
});