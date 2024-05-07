/* 다음API주소 */
function execDaumPostcode() {
  new daum.Postcode({
      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ""; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById("postcode").value = data.zonecode;
          document.getElementById("address").value = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById("detailAddress").focus();
      }
  }).open();
}

// 주소 검색 버튼 클릭시 
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);
console.log(execDaumPostcode);
// 회원 가입 유효성 검사 항목 
const checkObj ={
  "memberEmail" : false,
  "emailCheck" : false,
  "memberPw" : false,
  "memberPwConfirm" : false,
  "memberNickname" : false,
  "gender" : false,
  "memberTel" : false,
  "year" : false,
  "month" : false,
  "day" : false,
  "memberAddress" : false
};    
// ------------------------------------------------------------------

const memberEmail = document.querySelector("#memberEmail");
const signUpMessage = document.querySelector("#signUpMessage");
// 이메일 유효성 검사
memberEmail.addEventListener("input", e => {

  checkObj.emailCheck = false;
  document.querySelector("#authKeyMessage").innerText = "";

  checkObj.emailCheck = false;

  document.querySelector("#authKeyMessage").innerText ="";

  const inputEmail = e.target.value;

  if(inputEmail.trim().length === 0){

    signUpMessage.innerText="메일을 받을 수 있는 이메일을 입력해주세요"

    signUpMessage.classList.remove('confirm','error');

    checkObj.memberEmail = false;

    memberEmail.value="";

    return;
  }
  const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

if( !regExp.test(inputEmail)){
  
  signUpMessage.innerText="알맞은 이메일 형식으로 작성해주세요."

  signUpMessage.classList.add('error');

  signUpMessage.classList.remove('confirm');

  checkObj.memberEmail = false;

  return;
}
// 비동기로 작업
  fetch("/member/checkEmail?memberEmail=" + inputEmail)
  .then( resp => resp.text())
  .then( count => {

    if(count == 1){
      signUpMessage.innerText = "중복된 이메일 입니다.";
      signUpMessage.classList.add('error');
      signUpMessage.classList.remove('confirm');
      checkObj.memberEmail = false;
      return;
    }

    signUpMessage.innerText = "사용 가능한 이메일 입니다.";
    signUpMessage.classList.add('confirm');
    signUpMessage.classList.remove('error');
    checkObj.memberEmail = true;
  })
  .catch(error => {
    console.log(error);
  });

});
// ----------------------------------------------------------------------------
// 이메일 인증 

// 인증번호 받기 
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

// 인증번호 입력 
const emailCheck = document.querySelector("#emailCheck");

// 인증번호 입력 후 확인 버튼
const authKeyBtn = document.querySelector("#authKeyBtn");
// span 메시지 
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

  checkObj.emailCheck = false;
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
      checkObj.emailCheck = false;
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
authKeyBtn.addEventListener("click", ()=> {

  if( min === 0 && sec === 0){
    
    alert("인증번호 입력 제한 시간을 초과하였습니다! ");
    return;

  }

  if(emailCheck.value.length < 6){
    
    alert("인증번호를 정확히 입력해주세요");
    return;
  
  }
  const obj = {
    "email" : memberEmail.value,
    "authKey" : emailCheck.value
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
      checkObj.emailCheck = false;
      return;
    }

    clearInterval(authTimer);
    authKeyMessage.innerText = "인증 되었습니다."
    authKeyMessage.classList.remove("error");
    authKeyMessage.classList.add("confirm");

    checkObj.emailCheck = true;

  });

});
// -------------------------------------------------------------------
/* 비밀번호 / 비밀번호 확인 유효성 검사 */

// 1) 비밀번호 관련 요소 얻어오기
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

// 5) 비밀번호, 비밀번호 확인이 같은지 검사하는 함수 
const checkPw =() => {
    // 같은 경우 
    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText ="비밀번호가 일치합니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPwConfirm= true; // 비밀번호 확인 true
        return;
    }
    pwMessage.innerText = "비밀번호가 일치하지 않습니다."
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    checkObj.memberPwConfirm = false; // 비밀번호 확인 false;
};

// 2) 비밀번호 유효성 검사 
memberPw.addEventListener("input", e=> {

    // 입력 받은 비밀번호 값
    const inputPw = e.target.value;

    // 3) 입력되지 않은 경우 
    if(inputPw.trim().length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove("confirm","error");
        checkObj.memberPw = false; // 비밀번호가 유효하지 않다고 표시 
        memberPw.value = ""; // 처음에 띄어쓰기 입력 못하게 하기 
        return;
    }
    // 4) 입력 받은 비밀번호 정규식 검사 
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    if( !regExp.test(inputPw)){ //유효하지 않으면 
        pwMessage.innerText = "비밀번호가 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
        return;
    }

    // 유효한 경우 
    pwMessage.innerText = "유효한 비밀번호 형식입니다.";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPw = true;

    // 비밀번호 입력 시 확인란의 값과 비교하는 코드 추가 

    // 비밀번호 확인에 값이 작성되어 있을 때 
    if(memberPwConfirm.value.length > 0){
        checkPw();
    }
});

// 6) 비밀번호 확인 유효성 검사 
// 단, 비밀번호가 유효할 때만 검사 수행 
memberPwConfirm.addEventListener("input",()=>{
    
  if(!checkObj.memberPw){
    pwMessage.innerText = "비밀번호를 먼저 입력해주세요";
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    memberPw.focus();
    memberPwConfirm.value="";
    checkObj.memberPwConfirm = false;

    return;

  }

  
  checkPw();
});
// -------------------------------------------------------------------
// 전화번호 유효성 검사 
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberTel.addEventListener("input", e=>{
    const inputTel=e.target.value;
     if(inputTel.trim().length === 0){

        telMessage.innerText="전화번호를 입력해주세요.";
        telMessage.classList.remove("error,confirm");
        checkObj.memberTel=false;
        memberTel.value="";

        return;
     }
     const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

     if(!regExp.test(inputTel)){
        telMessage.innerText="유효하지 않은 전화번호 형식입니다.";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        checkObj.memberTel = false;

        return;
     }
     telMessage.innerText="사용 가능한 전화번호 입니다";
     telMessage.classList.add("confirm");
     telMessage.classList.remove("error");
     checkObj.memberTel= true;

});
// -------------------------------------------------------------------
// 닉네임 유효성 검사 
const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

memberNickname.addEventListener("input", e =>{
    const inputNickname = e.target.value;

    if(inputNickname.trim().length === 0){
        nickMessage.innerText="한글,영어,숫자로만 2~10글자";
        nickMessage.classList.remove("confirm","error");
        checkObj.memberNickname = false;
        memberNickname.value = "";
        return;
    }
    const regExp = /^[가-힣\w\d]{2,10}$/;

    if( !regExp.test(inputNickname)){
        nickMessage.innerText= "유효하지 않은 닉네임 형식 입니다.";
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");
        checkObj.memberNickname = false;
        return;

    }

    fetch("/member/checkNickname?memberNickname="+inputNickname)
    .then(resp => resp.text())
    .then(count => {
        if(count == 1){
            nickMessage.innerText= "이미 사용중인 닉네임 입니다.";
            nickMessage.classList.add("error");
            nickMessage.classList.remove("confirm");
            checkObj.memberNickname = false;

            return;
        }
        nickMessage.innerText ="사용 가능한 닉네임 입니다.";
        nickMessage.classList.add("confirm");
        nickMessage.classList.remove("error");
        checkObj.memberNickname= true;

    })
    .catch(err=> console.log(err));

});

// -------------------------------------------------------------------
// 성별 선택이 안되었을 시 
const gender = document.querySelector("#gender");

gender.addEventListener("change", function() {
    const selectedGender = this.value;
    
    // "성별" 이외의 값을 선택한 경우에만 경고창 표시
    if (selectedGender !== "F" && selectedGender !== "M") {
        alert("성별을 선택해주세요.");
        checkObj.gender = false;
    }

    checkObj.gender = true;
});

// -------------------------------------------------------------------
/* 생년 월일 */
const birthYearEl = document.querySelector('#year');
const birthMonthEl = document.querySelector('#month');
const birthDayEl = document.querySelector('#day');


isYearOptionExisted = false;
isMonthOptionExisted = false;
isDayOptionExisted = false;
// 연도
birthYearEl.addEventListener('focus', function () {
  // year 목록 생성되지 않았을 때 (최초 클릭 시)
  if(!isYearOptionExisted) {
    isYearOptionExisted = true
    for(var i = 1940; i <= 2022; i++) {
      // option element 생성
      const YearOption = document.createElement('option')
      YearOption.setAttribute('value', i)
      YearOption.innerText = i
      // birthYearEl의 자식 요소로 추가
      this.appendChild(YearOption);
    }
  }
});

// 월 
birthMonthEl.addEventListener('focus', function () {
    // month 목록 생성되지 않았을 때 (최초 클릭 시)
    if(!isMonthOptionExisted) {
        isMonthOptionExisted = true
      for(var i = 1; i <= 12; i++) {
       
        const monthOption = document.createElement('option')
        monthOption.setAttribute('value', i)
        monthOption.innerText = i
      
        this.appendChild(monthOption);
      }
    }
  });

// 일
birthDayEl.addEventListener('focus', function () {
    // month 목록 생성되지 않았을 때 (최초 클릭 시)
    if(!isDayOptionExisted) {
        isDayOptionExisted = true
      for(var i = 1; i <= 31; i++) {
       
        const dayOption = document.createElement('option')
        dayOption.setAttribute('value', i)
        dayOption.innerText = i
      
        this.appendChild(dayOption);
      }
    }
    console.log(checkObj);
  }); 

// ------------------------------------------------------------------
// 생년월일 연도 미체크 시 
birthYearEl.addEventListener("change", e => {

  if(e.target.value == 'year'){
    alert("년도를 선택해주세요.");
    checkObj.year = false;
    console.log(e);
    return;
  }
  checkObj.year = true;

});
// 생년 월 미체크 시

birthMonthEl.addEventListener("change",e =>{
  if(e.target.value == 'month'){
    alert("월을 선택해주세요.");
    checkObj.month = false;
    return;
  }
  checkObj.month = true;
});

birthDayEl.addEventListener("change", e=> {

  if(e.target.value == 'day'){
    alert("일을 선택해주세요.");
    checkObj.day = false;
    return;
  }
  checkObj.day = true;
});

// -----------------------------------------------------------------------------
// // 주소를 만약에 하나라도 넣었을 경우 
// const memberAddress = document.querySelectorAll("[name='memberAddress']");

// memberAddress.forEach(Address => {
//   Address.addEventListener("input", e => {

//    // 주소 
//    const addr0 = memberAddress[0].value.trim().length == 0;
//    const addr1 = memberAddress[1].value.trim().length == 0;
//    const addr2 = memberAddress[2].value.trim().length == 0;
  
//    // 모두 true인 경우 
//    const result1 = addr0 && addr1 && addr2;
//    // 모두 flase인 경우
//    const result2 = !(addr0 || addr1 || addr2);
   
//    // 모두 입력 또는 모두 미입력이 아니면
//    if( !(result1 || result2) ) {
//        alert("주소를 모두 작성 또는 미작성 해주세요.");
//        checkObj.memberAddress = false;
//        return;
//    }

//    checkObj.memberAddress = true;

//   });

// });


// -----------------------------------------------------------------------------

  // 회원 가입 폼 제출 
  const signUpForm = document.querySelector("#signUpForm");

  signUpForm.addEventListener("submit", e =>{
    const memberAddress = document.querySelectorAll("[name='memberAddress']");

    // 주소 
    const addr0 = memberAddress[0].value.trim().length == 0;
    const addr1 = memberAddress[1].value.trim().length == 0;
    const addr2 = memberAddress[2].value.trim().length == 0;
    
    // 모두 true인 경우 
    const result1 = addr0 && addr1 && addr2;
    // 모두 flase인 경우
    const result2 = !(addr0 || addr1 || addr2);
    
    // 모두 입력 또는 모두 미입력이 아니면
    if( !(result1 || result2) ) {
        alert("주소를 모두 작성 또는 미작성 해주세요.");
        e.preventDefault();
        checkObj.memberAddress = false;
        return;
    }

    checkObj.memberAddress = true;
    
    for(let key in checkObj){

      if( !checkObj[key] ){

        let str; 

        switch(key){
          case "memberEmail" : str = "이메일이 유효하지 않습니다"; break;
          case "emailCheck" : str = "이메일이 인증되지 않았습니다."; break;
          case "memberPw" : str = "비밀번호가 유효하지 않습니다."; break;
          case "memberPwConfirm" : str = "비밀번호가 일치하지 않습니다."; break;
          case "memberNickname" : str = "닉네임이 유효하지 않습니다."; break;
          case "gender" : str ="성별을 선택해주세요"; break;
          case "memberTel" : str = "전화번호가 유효하지 않습니다."; break; 
          case "year" : str ="연도를 선택해주세요."; break;
          case "month" : str ="월을 선택해주세요."; break;
          case "day" : str = "일을 선택해주세요."; break;
          case "memberAddress" : str = "주소를 모두 입력 또는 미작성 해주세요."; break
        }
        alert(str);

        document.getElementById(key).focus();
        e.preventDefault();
        return;
      }
    }

  });



