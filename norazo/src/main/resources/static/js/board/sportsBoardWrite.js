// 인원수 1~50 옵션 생성 코드
const memberCountSelect = document.querySelector(".member-count-select");

isDayOptionExisted = false;
memberCountSelect.addEventListener('focus', function() {
    if(!isDayOptionExisted) {
        isDayOptionExisted = true
      for(var i = 1; i <= 50; i++) {
       
        const count = document.createElement('option')
        count.setAttribute('value', i)
        count.innerText = i
      
        this.appendChild(count);
      }
    }
}); 

// 작성 폼 유효성 검사
const boardWriteFrm = document.querySelector("#boardWriteFrm");

const boardTitle = document.querySelector(".title-input");
const thumbnail = document.querySelector("#thumbnail");
const meetingDate = document.querySelector("[name='meetingDate']");
const categoryOption = document.querySelector(".category-option");
const meetingLocation = document.querySelector("[name='meetingLocation']");
const memberCountLimit = document.querySelector("[name='memberCountLimit']");
const boardContent = document.querySelector("[name='boardContent']");

