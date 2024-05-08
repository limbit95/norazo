function updateMemberInfo() {
    // Get the element by its ID
    const memberInfo = document.getElementById('memberInfo');
  
    // Read data attributes for attend and limit
    const attendMemberCount = parseInt(memberInfo.getAttribute('data-attend'), 10);
    const memberCountLimit = parseInt(memberInfo.getAttribute('data-limit'), 10);
  
    // Calculate the remaining spots
    const remaining = memberCountLimit - attendMemberCount;
  
    // Update the text content of the element
    memberInfo.textContent = `${attendMemberCount} / ${memberCountLimit} (${remaining} 자리 남음)`;
  }
  
  // Ensure the DOM is fully loaded before running the script
  document.addEventListener('DOMContentLoaded', updateMemberInfo);