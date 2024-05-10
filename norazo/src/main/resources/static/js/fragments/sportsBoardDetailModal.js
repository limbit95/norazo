document.querySelector(".personIco").addEventListener('click', function() {
    document.getElementById('modalContainer').style.display = 'flex'; // Show the modal
  });
  
const closeModalButton = document.querySelector(".closeModal");

function hideModal() {
    document.getElementById('modalContainer').style.display = 'none';
}

// Add click event listener to the close button
if (closeModalButton) {
    closeModalButton.addEventListener('click', function() {
        hideModal(); // Hide the modal on button click
    });
}

// Add keydown event listener to the whole document
document.addEventListener('keydown', function(event) {
    // Check if the Escape key is pressed
    if (event.key === "Escape") {
        hideModal(); // Hide the modal on Escape key press
    }
});