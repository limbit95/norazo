// 게시글 목록 썸네일, 제목 요소
const thumbnail = document.querySelectorAll(".thumbnail");
const boardTitle = document.querySelectorAll(".board-title"); 

// 게시글 클릭시 해당 게시글에 대한 모달창 요소들
const modalContent = document.querySelector(".modal-content");
const closeBtn = document.querySelector(".close-btn"); 

// 게시글 내용 담을 모달창 요소
const modalTitle = document.querySelector(".modal-title");
const modalThumbnail = document.querySelector(".modal-thumbnail");
const date = document.querySelector(".date");
const place = document.querySelector(".place");
const memberCount = document.querySelector(".memberCount");

// 참석 인원 현황 영억
const memberListDiv = document.querySelector(".memberList-div");

// join 버튼 요소
const joinBtn = document.querySelector("#join-btn");

// 좋아요 버튼
const boardLike = document.querySelector(".board-like");

let sportsCode;
let boardNo;
let likeCheck;

// 썸네일 클릭시 모달창 조회
thumbnail.forEach( (i) => {
    i.addEventListener("click", e => {
        // 조회한 모임글 작성자 회원 번호
        boardWriteMemberNo = e.target.dataset.memberNo;
        // 조회한 모임글 스포츠 종류
        sportsCode = e.target.dataset.sportsCode;
        // 조회한 모임글 번호
        boardNo = e.target.dataset.boardNo;
        
        // 참석 버튼에 현재 조회한 모임글 번호 데이터 삽입
        joinBtn.setAttribute("data-board-no", boardNo);

        // 기존에 조회했던 회원 리스트 div 비우기
        memberListDiv.removeChild(memberListDiv.childNodes[0]);

        fetch("/sportsBoard/modal?boardNo=" + boardNo)
        .then(resp => resp.json())
        .then(board => {
            modalTitle.innerText = board.boardTitle;
            modalThumbnail.setAttribute("src", board.thumbnail);
            date.innerText = board.meetingDate;
            place.innerText = board.meetingLocation;
            memberCount.innerText = board.attendMemberCount + " / " + board.memberCountLimit + " (" + (board.memberCountLimit-board.attendMemberCount) + "자리 남음)";
            
            // 참석 인원 가득차면 버튼 속성 변경
            if(board.memberCountLimit == board.attendMemberCount){
                joinBtn.innerText = "FULL";
                joinBtn.classList.add("full-member");
                joinBtn.classList.remove("join-btn");
            } else {
                joinBtn.innerText = "JOIN";
                joinBtn.classList.add("join-btn");
                joinBtn.classList.remove("full-member");
            }

            // boardLike.setAttribute("data-like-check", board.likeCheck);
            likeCheck = board.likeCheck;

            // 좋아요 체크 여부 확인
            if(board.likeCheck > 0){
                boardLike.setAttribute("name", "heart");
                boardLike.removeAttribute("name", "heart-outline");
            } else {
                boardLike.setAttribute("name", "heart-outline");
            }

            const memberList = document.createElement("div");
            memberList.classList.add("memberList"); 

            
            // ---------- 현재 참석한 회원 리스트 ----------
            board.memberList.forEach( (i, index) =>{
                const img = document.createElement("img");
                img.classList.add("member");

                if(index < 5){
                    if(i.profileImg == null){
                        img.setAttribute("src", "/images/profile/default-profileImg.png");
                    } else{
                        img.setAttribute("src", i.profileImg);
                    }
                    memberList.append(img);
                }

                if(index == 6){
                    img.setAttribute("src", "/images/profile/01_assets.png");
                    memberList.append(img);
                }
            });
            memberListDiv.append(memberList);
            // ---------- 현재 참석한 회원 리스트 ----------


            // 참석되어 있는 모임글은 모달창 조회가 아닌 상세 조회 페이지로 바로 이동
            if(loginMember != null) {
                board.memberList.forEach( (i) => {
                    if(loginMember.memberNo == i.memberNo){
                        location.href = "/sportsBoard/" + sportsCode + "/" + boardNo;
                        return;
                    }
                });
            }
        });
        // 모달창 보이기
        modalContent.classList.remove("popup-hidden");
    });

});

// 게시글 제목 클릭시 모달창 조회
boardTitle.forEach( (i) => {
    i.addEventListener("click", e => {

        // 조회한 게시글 작성자 회원 번호
        boardWriteMemberNo = e.target.dataset.memberNo;
        // 조회한 게시글 스포츠 종류
        sportsCode = e.target.dataset.sportsCode;
        // 조회한 게시글 번호
        boardNo = e.target.dataset.boardNo;
        if(loginMember != null) {
            if(loginMember.memberNo == boardWriteMemberNo){
                location.href = "/sportsBoard/" + sportsCode + "/" + boardNo;
                return;
            }
        }

        modalContent.classList.remove("popup-hidden");
        joinBtn.setAttribute("data-board-no", boardNo);

        memberListDiv.removeChild(memberListDiv.childNodes[0]);

        fetch("/sportsBoard/modal?boardNo=" + boardNo)
        .then(resp => resp.json())
        .then(board => {
            modalTitle.innerText = board.boardTitle;
            modalThumbnail.setAttribute("src", board.thumbnail);
            date.innerText = board.meetingDate;
            place.innerText = board.meetingLocation;
            memberCount.innerText = board.attendMemberCount + " / " + board.memberCountLimit + " (" + (board.memberCountLimit-board.attendMemberCount) + "자리 남음)";
            
            if(board.memberCountLimit == board.attendMemberCount){
                joinBtn.innerText = "full";
                joinBtn.classList.add("full-member");
                joinBtn.classList.remove("join-btn");
            } else {
                joinBtn.innerText = "join";
                joinBtn.classList.add("join-btn");
                joinBtn.classList.remove("full-member");
            }

            const memberList = document.createElement("div");
            memberList.classList.add("memberList"); 

            board.memberList.forEach( (i, index) =>{
                const img = document.createElement("img");
                img.classList.add("member");
                if(index < 6){
                    if(i.profileImg == null){
                        img.setAttribute("src", "/images/profile/default-profileImg.png");
                    } else{
                        img.setAttribute("src", i.profileImg);
                    }
                    memberList.append(img);
                }
            });

            memberListDiv.append(memberList)   ;       
        });

    });
});

// 닫기 버튼 클릭시 모달창 닫기
if(closeBtn != null){
    closeBtn.addEventListener("click", () => {
        modalContent.classList.add("popup-hidden");
    });
};

// 키보드 Esc 눌렀을 시 모달창 닫기
window.addEventListener("keydown", (e) => {
    if(e.key == 'Escape'){
        modalContent.classList.add("popup-hidden");
    };
});


// join 버튼 눌렀을 시

if(joinBtn != null){
    joinBtn.addEventListener("click", e => {
        if(loginMember == null){
            if(confirm("로그인 후 이용가능합니다. 로그인 페이지로 이동하시겠습니까?")){
                location.href = "/member/login";
            }
            return;
        }

        if(confirm("해당 모임에 참석하시겠습니까?")){
            location.href = "/sportsBoard/" + sportsCode + "/" + boardNo;
        }
    });
};



// 좋아요 버튼 눌렀을 때

if(boardLike != null){
    boardLike.addEventListener("click", e => {
        if(loginMember == null) {
            alert("로그인 후 사용 가능한 기능입니다.");
            return;
        }

        const obj = {
            "memberNo"  : loginMember.memberNo,
            "boardNo"   : boardNo,
            "likeCheck" : likeCheck
        }

        fetch("/sportsBoard/like", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                
            }
            likeCheck = likeCheck == 0 ? 1 : 0;
        });

        if(boardLike.getAttribute("name") == "heart-outline") {
            boardLike.setAttribute("name", "heart");
            boardLike.removeAttribute("name", "heart-outline");
            return;
        }

        if(boardLike.getAttribute("name") == null) {
            boardLike.setAttribute("name", "heart-outline");
            return;
        }
        
    });
}