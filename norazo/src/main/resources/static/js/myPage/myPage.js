/* 다음 주소 API 활용 */
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
if (document.querySelector("#searchAddress") !== null) {
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);
}

const updateInfo = document.querySelector("#updateInfo");

// 회원 가입 유효성 검사 항목 

const checkObj ={
  "memberNickname" : false,
  "gender" : true,
  "memberAddress" : false
};   


if (updateInfo !== null) {
    updateInfo.addEventListener("submit", e => {
        const memberAddress = document.querySelectorAll("[name='memberAddress']");
        const addr0 = memberAddress[0].value.trim().length == 0;
        const addr1 = memberAddress[1].value.trim().length == 0;
        const addr2 = memberAddress[2].value.trim().length == 0;
        const result1 = addr0 && addr1 && addr2;
        const result2 = !(addr0 || addr1 || addr2);
        if (!(result1 || result2)) {
            alert("주소를 모두 작성 또는 미작성 해주세요");
            e.preventDefault();
        }
    });
}

const changePw = document.querySelector("#changePw");

if (changePw !== null) {
    changePw.addEventListener("submit", e => {
        const currentPw = document.querySelector("#currentPw");
        const newPw = document.querySelector("#newPw");
        const newPwConfirm = document.querySelector("#newPwConfirm");
        if (currentPw.value.trim().length == 0 || newPw.value.trim().length == 0 || newPwConfirm.value.trim().length == 0) {
            alert("모든 비밀번호 필드를 입력하세요");
            e.preventDefault();
            return;
        }
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
        if (!regExp.test(newPw.value)) {
            alert("비밀번호는 6자 이상 20자 이하의 영문 대소문자, 숫자, 특수문자(!@#_-)만 사용할 수 있습니다");
            e.preventDefault();
            return;
        }
        if (newPw.value !== newPwConfirm.value) {
            alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다");
            e.preventDefault();
            return;
        }
    });
}

const secession = document.querySelector("#secession");

if (secession !== null) {
    secession.addEventListener("submit", e => {
        const memberPw = document.querySelector("#memberPw");
        const agree = document.querySelector("#agree");
        if (memberPw.value.trim().length == 0) {
            alert("비밀번호를 입력하세요");
            e.preventDefault();
            return;
        }
        if (!agree.checked) {
            alert("회원탈퇴를 위해 약관에 동의해주세요");
            e.preventDefault();
            return;
        }
        if (!confirm("정말 탈퇴 하시겠습니까?")) {
            alert("취소 되었습니다.");
            e.preventDefault();
            return;
        }
    });
}

// 프로필 이미지 추가/변경/삭제
const profile = document.querySelector("#profile");

if (profile !== null) {
    let statusCheck = -1;
    let backupInput;

    const profileImg = document.querySelector("#profileImg");
    const imageInput = document.querySelector("#imageInput");
    const deleteImage = document.querySelector("#deleteImage");

    const changeImageFn = e => {
        const maxSize = 1024 * 1024 * 5;
        const file = e.target.files[0];

        if (file == undefined) {
            const temp = backupInput.cloneNode(true);
            imageInput.after(backupInput);
            imageInput.remove();
            imageInput = backupInput;
            imageInput.addEventListener("change", changeImageFn);
            backupInput = temp;
            return;
        }

        if (file.size > maxSize) {
            alert("5MB 이하의 이미지 파일을 선택해 주세요.");
            if (statusCheck == -1) imageInput.value = '';
            else {
                const temp = backupInput.cloneNode(true);
                imageInput.after(backupInput);
                imageInput.remove();
                imageInput = backupInput;
                imageInput.addEventListener("change", changeImageFn);
                backupInput = temp;
            }
            return;
        }

        const reader = new FileReader();
        reader.readAsDataURL(file);

        reader.addEventListener("load", e => {
            const url = e.target.result;
            profileImg.setAttribute("src", url);
            statusCheck = 1;
            backupInput = imageInput.cloneNode(true);
        });
    };

    imageInput.addEventListener("change", changeImageFn);

    deleteImage.addEventListener("click", () => {
        profileImg.src = "/images/user.png";
        imageInput.value = '';
        backupInput = undefined;
        statusCheck = 0;
    });

    profile.addEventListener("submit", e => {
        let flag = true;
        if (loginMemberProfileImg == null && statusCheck == 1) flag = false;
        if (loginMemberProfileImg != null && statusCheck == 0) flag = false;
        if (loginMemberProfileImg != null && statusCheck == 1) flag = false;
        if (flag) {
            e.preventDefault();
            alert("이미지 변경 후 클릭하세요");
        }
    });
}

const loginNickname = loginMember.memberNickname;
// 닉네임 유효성 검사
var flag1 = false;
const memberNickname = document.querySelector("#memberNickname");
const confirm1 = document.querySelector("#confirm");
if(confirm1 != null){
	confirm1.addEventListener("click", e => {
        flag1 = true;
        if(memberNickname.value.trim().length === 0) {
            alert("닉네임을 입력해주세요");
            e.preventDefault(); // 제출 막기
            return;
        }
        const regExp = /^[가-힣\w\d]{2,10}$/;
        if( !regExp.test(memberNickname.value)) {
            alert("닉네임이 유효하지 않습니다.");
            e.preventDefault(); // 제출 막기
            return;
        }

        fetch("/member/checkNickname?memberNickname=" + memberNickname.value)
            .then(resp => resp.text())
            .then(count => {
                if (count == 1) {
                    alert("이미 사용중인 닉네임 입니다.");
                    
                    if(loginNickname == memberNickname) {
                        checkObj.memberNickname = true;
                    }else{
                        checkObj.memberNickname = false;
                        console.log("안됨");
                    }
                    return;
                }
                alert("사용 가능한 닉네임 입니다.");
                checkObj.memberNickname = true;
            })
            .catch(err => console.log(err));
    });
}   


// 성별 선택이 안되었을 시 
const gender = document.querySelector("#gender");

gender.addEventListener("change", function() {


    const selectedGender = this.value;

    if (selectedGender !== "F" && selectedGender !== "M") {

        checkObj.gender = false;
        return;
    }

    checkObj.gender = true;
});





      // 회원 가입 폼 제출 
  const signUpForm = document.querySelector("#updateInfo");
if(signUpForm != null) {
  signUpForm.addEventListener("submit", e =>{
    const memberAddress = document.querySelectorAll("[name='memberAddress']");
    const addressMessage = document.querySelector("#addressMessage");
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
    
    if (flag1){
    for(let key in checkObj){

        
        if( !checkObj[key] ){
          let str; 
  
          switch(key){
            case "memberNickname" : str = "닉네임이 유효하지 않습니다."; break;
            case "gender" : str ="성별을 선택해주세요"; break;
            case "memberAddress" : str = "주소를 모두 입력 또는 미작성 해주세요."; break
          }
          alert(str);
  
          document.getElementById(key).focus();
          e.preventDefault();
          return;


        }}
    }
  });
}
const introduce = loginMember.memberIntroduce;
if (introduce != null) {
    document.getElementById('introduce').innerHTML = introduce;
}
