const modalContent = document.querySelector(".modal-content");
const thumbnail = document.querySelectorAll(".thumbnail");
const closeBtn = document.querySelector(".close-btn"); 
const boardTitle = document.querySelectorAll(".board-title"); 

thumbnail.forEach( (i) => {
    i.addEventListener("click", () => {
        modalContent.classList.remove("popup-hidden");
    });
})

boardTitle.forEach( (i) => {
    i.addEventListener("click", () => {
        modalContent.classList.remove("popup-hidden");
    });
})

closeBtn.addEventListener("click", () => {
    modalContent.classList.add("popup-hidden");
});