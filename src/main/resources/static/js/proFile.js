
  document.addEventListener("DOMContentLoaded", function () {
    const profileDropdown = document.getElementById("profileDropdown");

    profileDropdown.querySelector(".profile-icon").addEventListener("click", function (e) {
      e.stopPropagation(); // 이벤트 버블링 방지
      profileDropdown.classList.toggle("active");
    });

    document.addEventListener("click", function (e) {
      if (!profileDropdown.contains(e.target)) {
        profileDropdown.classList.remove("active");
      }
    });
  });
