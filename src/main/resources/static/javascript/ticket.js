$(document).ready(function() {
    // Function to calculate fees and update the pricePerPerson field
    function calculateFees() {
        var totalFees = 0;

        $("#selections .form-row").each(function() {
            var establishmentId = $("#establishmentId").val();
            var nationalityId = $(this).find("#nationalities").val();
            var categoryId = $(this).find("#categories").val();
            var numberOfPeople = $(this).find("#numberOfPeople").val() || 0;

            // Send AJAX request to backend controller to fetch total fees
            $.ajax({
                url: '/fetchFee',
                method: 'GET',
                data: {
                    nationalityId: nationalityId,
                    categoryId: categoryId,
                    establishmentId: establishmentId,
                    numberOfPeople: numberOfPeople
                },
                success: function(response) {
                    // Update the total fees field with the fetched total fees
                    $(this).find("#totalFees").text(response.totalFees);

                    // Update the price per person field with the fetched entry fee
                    $(this).find(".pricePerPerson").text(response.entryFee);

                    // Add the fetched total fees to the running total
                    totalFees += parseFloat(response.totalFees);

                    // Update the Fees field with the total fees
                    $("#fees").text(totalFees.toFixed(2));
                    calculateTotalAmount();

                }.bind(this), // Bind the current context to use inside the success callback
                error: function(xhr, status, error) {
                    console.error('Error fetching/calculating fees:', error);
                }
            });
        });
    }
    // Function to calculate other fees and update the total item fees
    function calculateOtherFees() {
        var totalItemFees = 0;

        $("#feesList .feeEntryRow").each(function() {
            var numItems = $(this).find(".number-of-items").val();
            var feePerItem = $(this).find(".feePerItem").data("fee-per-item");

            // Calculate total item fees
            if (numItems && feePerItem) {
                var itemFees = numItems * feePerItem;
                $(this).find(".totalItemFees").val(itemFees.toFixed(2));
                totalItemFees += parseFloat(itemFees);
            } else {
                $(this).find(".totalItemFees").text(''); // Clear the total item fees if numItems is empty
            }
        });

        // Update the Item Fees field with the total item fees
        $("#itemFees").val(totalItemFees.toFixed(2));
        calculateTotalAmount();
    }

    // Function to calculate the total amount
    // Calculate total amount
    function calculateTotalAmount() {
        var fees = parseFloat($("#fees").text()) || 0;
        var itemFees = parseFloat($("#itemFees").text()) || 0;
        var totalAmount = fees + itemFees;

        $("#totalAmount").text(totalAmount.toFixed(2));
    }

    // Call calculateFees() whenever there is a change in nationality, category, establishment, or number of people
    $(document).on("change", "#nationalities, #categories, #numberOfPeople", function() {
        calculateFees();
    });

    // For nationality and category and total person calculation
    $(document).on("click", ".addFields", function() {
        var newFields = $("#selection1").clone(); // Clone the container for nationality and category
        newFields.find("select").val(""); // Clear select values
        newFields.find(".pricePerPerson").text(""); // Clear text for price per person
        newFields.find("#totalFees").text(""); // Clear text for total fees

        // Remove existing "Delete" button before appending
        newFields.find('.removeFields').parent().remove();

        // Append the new fields
        newFields.insertAfter("#selections .form-row:last");

        // Create a container for the "Delete" button to manage its width
        var deleteButtonContainer = $("<div>", {"class": "delete-button-container"});
        var deleteButton = $("<button>", {
            "type": "button",
            "class": "btn btn-danger removeFields",
            "text": "Delete"
        });

        // Append the delete button to its container and then to the DOM
        deleteButton.appendTo(deleteButtonContainer);
        deleteButtonContainer.insertAfter(newFields);
    });

// Event to handle the deletion of fields
    $(document).on("click", ".removeFields", function() {
        $(this).parent('.delete-button-container').prev('.form-row').remove(); // Remove the fields row
        $(this).parent('.delete-button-container').remove(); // Remove the delete button's container
        calculateFees();
        calculateTotalPersons();
    });


    // Check for existing combinations when selecting nationality and category
    $(document).on("change", ".form-group1 select", function() {
        calculateFees();
    });

    // Function to remove fields dynamically
    $(document).on("click", ".removeFields", function() {
        $(this).closest('.form-row').remove(); // Remove the closest form-row
        counter--;

        // Recalculate total persons when removing a row
        calculateTotalPersons();
    });

    // Check for existing combinations when selecting nationality and category
    $(document).on("change", ".form-group1 select", function() {
        var nationalityId = $(this).closest(".form-row").find("#nationalities").val();
        var categoryId = $(this).closest(".form-row").find("#categories").val();
        var existingCombination = false;

        // Only check for duplicate if both nationality and category are selected
        if (nationalityId && categoryId) {
            // Check if the combination already exists with the same category
            $("#selections .form-row").not($(this).closest(".form-row")).each(function() {
                if ($(this).find("#nationalities").val() == nationalityId &&
                    $(this).find("#categories").val() == categoryId) {
                    existingCombination = true;
                    return false; // Break the loop
                }
            });
        }

        if (existingCombination) {
            alert("The selected combination already exists with the same category.");
            $(this).val(""); // Reset the select value
        }

        // Recalculate total persons when changing a selection
        calculateTotalPersons();
    });

    // Calculate total persons
    function calculateTotalPersons() {
        var totalPersons = 0;

        // Sum up the number of people in each row
        $(".number-of-people").each(function() {
            var numberOfPeople = parseInt($(this).val());
            if (!isNaN(numberOfPeople)) {
                totalPersons += numberOfPeople;
            }
        });

        // Update the total persons field
        $("#totalPersons").text(totalPersons); // Use text() instead of val()
    }

// Ensure total persons are updated when number of people input is changed
    $(document).on("input", ".number-of-people", function() {
        calculateTotalPersons();
    });

});

function validateNumberOfPeople() {
    var numberOfPeople = document.getElementById('numberOfPeople').value;

    // Check if the number of people is not empty and not equal to 0
    if (numberOfPeople !== "" && numberOfPeople !== "0") {
        calculateTotalPersons(); // Calculate total persons if a valid value is entered
    } else {
        document.getElementById('totalPersons').value = ""; // Clear total persons if the number of people is empty or 0
    }
}

/// NonWorkingDays
$(document).ready(function() {
    var establishmentId = $('#establishmentId').val();
    console.log("Establishment ID:", establishmentId);

    $.ajax({
        url: '/fetchNonWorkingDays/' + establishmentId,
        method: 'GET',
        success: function(response) {
            console.log("Success response:", response);
            // Extract non-working dates and reasons
            var nonWorkingDates = [];
            $.each(response, function(index, item) {
                nonWorkingDates.push(item.nonWorkingDate); // Assuming nonWorkingDate is the field containing the date
            });

            console.log("Non-working dates:", nonWorkingDates);

            // Disable non-working dates
            $('#dateTime').on('input', function() {
                var selectedDate = $(this).val();
                if (nonWorkingDates.includes(selectedDate)) {
                    alert("This date is closed due to: " + response[nonWorkingDates.indexOf(selectedDate)].reason + "Please select another date");
                    $(this).val(''); // Clear the selected date
                }
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching non-working dates:', error);
        }
    });

    // Fetch and display other fees
    fetchOtherFees();

    function fetchOtherFees() {
        $.ajax({
            url: '/fetchOtherFees',
            method: 'GET',
            data: { establishmentId: establishmentId },
            success: function(response) {
                var feesList = $("#feesList");
                feesList.empty(); // Clear existing rows

                response.forEach(function(fee) {
                    var feeEntryRow = `
                            <div class="row full-width">
                                <div class="col-lg-3">
                                    <label>Fee Type:</label>
                                    <span>${fee.feesType}</span>
                                </div>
                                <div class="col-lg-3">
                                    <label>Fee per Item:</label>
                                    <span class="feePerItem" data-fee-per-item="${fee.feePerItem}">${fee.feePerItem}</span>
                                </div>
                                <div class="col-lg-3" style="flex-basis: 100%;">
                                    <label for="numItems_${fee.feesType}">Number of Items:</label>
                                    <input type="number" id="numItems_${fee.feesType}" class="number-of-items number-of-items-input" min="1" data-feetype="${fee.feesType}" />
                                </div>
                                <div class="col-lg-3" style="flex-basis: 100%;">
                                    <label>Total Item Fees:</label>
                                    <span id="totalItemFees_${fee.feesType}" class="totalItemFees"></span>
                                </div>
                            </div>`;

                    feesList.append(feeEntryRow);
                });

                // Bind event listener to number of items input
                $(document).on("input", ".number-of-items", function() {
                    calculateOtherFees();
                });

                // Ensure total item fees are updated when number of items is changed
                $(document).on("input", ".number-of-items", function() {
                    calculateOtherFees();
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching other fees:', error);
            }
        });
    }
});
// Function to calculate other fees and update the total item fees
function calculateOtherFees() {
    var totalItemFees = 0;
    var totalItemsCount = 0;

    $(".number-of-items").each(function() {
        var feeType = $(this).data("feetype");
        var numItems = parseInt($(this).val()) || 0;
        var feePerItem = $(this).closest('.feeEntryRow').find(".feePerItem").data("fee-per-item");

        // Calculate total item fees
        if (numItems && feePerItem) {
            var itemFees = numItems * feePerItem;
            $(this).closest('.feeEntryRow').find(".totalItemFees").text(itemFees.toFixed(2));
            totalItemFees += parseFloat(itemFees);
        } else {
            $(this).closest('.feeEntryRow').find(".totalItemFees").text(''); // Clear total item fees if numItems is empty
        }

        // Sum the total number of items
        totalItemsCount += numItems;
    });

    // Update the Item Fees field with the total item fees
    $("#itemFees").text(totalItemFees.toFixed(2));
    // Update the Total Items field with the total number of items
    $("#totalItems").text(totalItemsCount);
    calculateTotalAmount();
}

// Ensure total item fees and total items are updated when number of items is changed
$(document).on("input", ".number-of-items", function() {
    calculateOtherFees();

});
