document.addEventListener("DOMContentLoaded", function () {

    const button = document.getElementById("genderButton");
    const buttonText = document.getElementById("genderButtonText");
    const dropdown = document.getElementById("genderDropdown");
    const hiddenInput = document.getElementById("genderInput");
    const options = document.querySelectorAll(".gender-option");

    if (!button || !dropdown || !hiddenInput) return;

    // Toggle dropdown
    button.addEventListener("click", function (e) {
        e.stopPropagation();
        dropdown.classList.toggle("hidden");
    });

    // Option click
    options.forEach(option => {
        option.addEventListener("click", function () {
            const value = this.getAttribute("data-value");

            // Update button text
            buttonText.textContent = value;

            // Store value for form submit
            hiddenInput.value = value;

            // Close dropdown
            dropdown.classList.add("hidden");
        });
    });

    // Close when clicking outside
    document.addEventListener("click", function () {
        dropdown.classList.add("hidden");
    });
});
