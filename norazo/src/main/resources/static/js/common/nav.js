const searchQuery = document.querySelector("#searchQuery")
const searchClear = document.querySelector(".searchClear")

searchQuery.addEventListener("input", e => {
    if(searchQuery.value.trim().length == 0){
        searchClear.classList.add("icon-hidden");
        return;
    }
    searchClear.classList.remove("icon-hidden");
});

searchClear.addEventListener("click", e => {
    searchQuery.value = "";
    searchClear.classList.add("icon-hidden");
});

