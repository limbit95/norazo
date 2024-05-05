// 게시글 목록 썸네일, 제목 요소
const thumbnail = document.querySelectorAll(".thumbnail");
const boardTitle = document.querySelectorAll(".board-title"); 

// 게시글 클릭시 해당 게시글에 대한 모달창 요소들
const modalContent = document.querySelector(".modal-content");
const closeBtn = document.querySelector(".close-btn"); 

const modalTitle = document.querySelector(".modal-title");
const modalThumbnail = document.querySelector(".modal-thumbnail");
const date = document.querySelector(".date");
const place = document.querySelector(".place");
const member = document.querySelector(".member");

thumbnail.forEach( (i) => {
    i.addEventListener("click", e => {
        modalContent.classList.remove("popup-hidden");

        const boardNo = e.target.dataset.boardNo;

        fetch("/sportsBoard/modal?boardNo=" + boardNo)
        .then(resp => resp.json())
        .then(board => { 
            modalTitle.innerText = board.boardTitle;
            modalThumbnail.setAttribute("src", board.thumbnail);
            date.innerText = board.meetingDate;
            place.innerText = board.meetingLocation;
            member.innerText = board.attendMember + " / " + board.memberCountLimit + " (" + (board.memberCountLimit-board.attendMember) + "자리 남음)";
        });

    });

});

boardTitle.forEach( (i) => {
    i.addEventListener("click", (e) => {
        modalContent.classList.remove("popup-hidden");
        console.log(e.target.dataset.boardNo);
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