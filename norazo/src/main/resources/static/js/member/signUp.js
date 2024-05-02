// /* 다음 주소 API 활용 */
// function execDaumPostcode() {
//   new daum.Postcode({
//       oncomplete: function(data) {
//           // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

//           // 각 주소의 노출 규칙에 따라 주소를 조합한다.
//           // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
//           var addr = ''; // 주소 변수

//           //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//           if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
//               addr = data.roadAddress;
//           } else { // 사용자가 지번 주소를 선택했을 경우(J)
//               addr = data.jibunAddress;
//           }

//           // 우편번호와 주소 정보를 해당 필드에 넣는다.
//           document.getElementById('postcode').value = data.zonecode;
//           document.getElementById("address").value = addr;
//           // 커서를 상세주소 필드로 이동한다.
//           document.getElementById("detailAddress").focus();
//       }
//   }).open();
// }
// // 주소 검색 버튼 클릭시 

// document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);

// 회원 가입 유효성 검사 항목 
const checkObj ={
  "memberEmail" : false,
  "emailCheck" : false,
  "memberPw" : false,
  "memberPwConfirm" : false,
  "memberNickname" : false,
  "gender" : false
};    
// ------------------------------------------------------------------

const memberEmail = document.querySelector("#memberEmail");
const signUpMessage = document.querySelector("#signUpMessage");
// 이메일 유효성 검사
memberEmail.addEventListener("input", e => {

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

  // ---------------------------------------------------
  authKeyMessage.innerText = initTime;
  authKeyMessage.classList.remove("confirm","error");

  alert("인증번호가 발송 되었습니다.");

  authTimer = setInterval( () => {

    authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    if(min == 0 && sec == 0){
      checkObj.emailCheck = false;
      clearInterval
    }

  },1000);
});

function addZero(number){
  if(number < 10) return "0" + number;
  else return number;
}
// -------------------------------------------------------------------
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");


// 2) 비밀번호 유효성 검사 

// 비밀번호, 비밀번호확인이 같은지 검사하는 함수 

const checkPw = () =>{

    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText = "비밀번호가 일치합니다.";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPwConfirm = true;
        return;
    }

    pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    checkObj.memberPwConfirm = false;

}

memberPw.addEventListener("input", e =>{

    const inputPw = e.target.value;

    if(inputPw.trim().length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove("error","confirm");
        checkObj.memberPw = false;
        memberPw.value="";
        return;
    }
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    if( !regExp.test(inputPw) ){
        pwMessage.innerText = "비밀번호가 유효하지 않습니다";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
        return;
    }
    pwMessage.innerText= "유효한 비밀번호 형식입니다.";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPw= true;

    // 비밀번호 입력시 확인란의 값과 비교하는 코드 추가 

    // 비밀번호 확인에 값이 작성되어 있을 때

    if(memberPwConfirm.value.length > 0){
        checkPw();
    }
});

// 비밀번호 확인 유효성 검사
// 단, 비밀번호가 유효할 때만 검사 수행
memberPwConfirm.addEventListener("input",() => {

    if(checkObj.memberPw){
        checkPw();
        return;
    }

    checkObj.memberPwConfirm = false;
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
  }); 
  
  // --------------------------------------------


