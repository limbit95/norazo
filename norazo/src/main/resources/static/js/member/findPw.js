// 메일이 있는지 검사  
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");


const checkObj = {"memberEmail" : false};

let inputEmail;
    
memberEmail.addEventListener("input", e => {

    inputEmail = e.target.value;
    
    if( inputEmail.trim().length === 0){
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요";
        emailMessage.classList.remove("confirm","error");
        checkObj.memberEmail = false;
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

  
// -------------------------------------------------------------------

console.log(checkObj);

const findPwForm = document.querySelector("#findPwForm");

findPwForm.addEventListener("submit", e =>{


   if(!checkObj.memberEmail){

    e.preventDefault();

    memberEmail.focus();

    return;

   }
});


