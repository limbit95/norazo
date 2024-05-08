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

const 