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
  