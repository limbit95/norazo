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

// 썸네일 클릭시 모달창 조회
thumbnail.forEach( (i) => {
    i.addEventListener("click", e => {
        modalContent.classList.remove("popup-hidden");
        const boardNo = e.target.dataset.boardNo;

        memberListDiv.removeChild(memberListDiv.childNodes[0]);

        fetch("/sportsBoard/modal?boardNo=" + boardNo)
        .then(resp => resp.json())
        .then(board => {
            modalTitle.innerText = board.boardTitle;
            modalThumbnail.setAttribute("src", board.thumbnail);
            date.innerText = board.meetingDate;
            place.innerText = board.meetingLocation;
            memberCount.innerText = board.attendMemberCount + " / " + board.memberCountLimit + " (" + (board.memberCountLimit-board.attendMemberCount) + "자리 남음)";
            
            const memberList = document.createElement("div");
            memberList.classList.add("memberList"); 

            board.member.forEach( (i) =>{
                const img = document.createElement("img");
                img.classList.add("member");
                if(i.profileImg == null){
                    img.setAttribute("src", "/images/profile/default-profileImg.png");
                } else{
                    img.setAttribute("src", i.profileImg);
                }
                memberList.append(img);
            });

            memberListDiv.append(memberList)   ;       
        });

    });

});

// 게시글 제목 클릭시 모달창 조회
boardTitle.forEach( (i) => {
    i.addEventListener("click", e => {
        modalContent.classList.remove("popup-hidden");
        const boardNo = e.target.dataset.boardNo;

        memberListDiv.removeChild(memberListDiv.childNodes[0]);

        fetch("/sportsBoard/modal?boardNo=" + boardNo)
        .then(resp => resp.json())
        .then(board => {
            modalTitle.innerText = board.boardTitle;
            modalThumbnail.setAttribute("src", board.thumbnail);
            date.innerText = board.meetingDate;
            place.innerText = board.meetingLocation;
            memberCount.innerText = board.attendMemberCount + " / " + board.memberCountLimit + " (" + (board.memberCountLimit-board.attendMemberCount) + "자리 남음)";
            
            const memberList = document.createElement("div");
            memberList.classList.add("memberList"); 

            board.member.forEach( (i) =>{
                const img = document.createElement("img");
                img.classList.add("member");
                if(i.profileImg == null){
                    img.setAttribute("src", "/images/profile/default-profileImg.png");
                } else{
                    img.setAttribute("src", i.profileImg);
                }
                memberList.append(img);
            });

            memberListDiv.append(memberList)   ;       
        });

    });
});





if(closeBtn != null){
    closeBtn.addEventListener("click", () => {
        modalContent.classList.add("popup-hidden");
    });
};


window.addEventListener("keydown", (e) => {
    if(e.key == 'Escape'){
        modalContent.classList.add("popup-hidden");
    };
});