$(document).ready(function() {
	$('#status').change(function(e) {
		if ($('#status option:selected').text() === 'New Status')
			$('#newStatus').show();
		else
			$('#newStatus').hide();
	})
});
	

	  document.addEventListener("DOMContentLoaded", function() {

	    // JavaScript form validation

	    var checkPassword = function(str)
	    {
	      var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
	      return re.test(str);
	    };

	    var checkForm = function(e)
	    {
	      
	      if(this.password.value != "" && this.password.value == this.confirmPassword.value) {
	        if(!checkPassword(this.password.value)) {
	          alert("The password you have entered is not valid!");
	          this.password.focus();
	          e.preventDefault();
	          return;
	        }
	      } else {
	        alert("Error: Please check that you've entered and confirmed your password!");
	        this.password.focus();
	        e.preventDefault();
	        return;
	      }
	      alert("Both username and password are VALID!");
	    };

	    var myForm = document.getElementById("myForm");
	    myForm.addEventListener("submit", checkForm, true);

	    // HTML5 form validation

	    var supports_input_validity = function()
	    {
	      var i = document.createElement("input");
	      return "setCustomValidity" in i;
	    }

	    if(supports_input_validity()) {
	     

	      var passwordInput = document.getElementById("field_password");
	      passwordInput.setCustomValidity(passwordInput.title);

	      var confirmPasswordInput = document.getElementById("field_confirmPassword");

	      // input key handlers

	     
	      passwordInput.addEventListener("keyup", function() {
	        this.setCustomValidity(this.validity.patternMismatch ? passwordInput.title : "");
	        if(this.checkValidity()) {
	          confirmPasswordInput.pattern = this.value;
	          confirmPasswordInput.setCustomValidity(confirmPasswordInput.title);
	        } else {
	          confirmPasswordInput.pattern = this.pattern;
	          confirmPasswordInput.setCustomValidity("");
	        }
	      }, false);

	      confirmPasswordInput.addEventListener("keyup", function() {
	        this.setCustomValidity(this.validity.patternMismatch ? confirmPasswordInput.title : "");
	      }, false);

	    }

	  }, false);
});

$(document).ready(function() {
	$('#showCoupon1').click(function(e) {
			$('#viewCoupon1').show();
			$('#showCoupon1').hide();
	})
});
		
$(document).ready(function() {
	$('#companySignup').click(function(e) {
			$('#companyRegistration').show();

			$('#someId').hide();
	})
});

$(document).ready(function() {
	$('#signup').click(function(e) {
			$('#companyRegistration').hide();

			$('#someId').show();
	})
});

$(document).ready(function() {
	$('#forgotPass').click(function(e) {
			$('#inputEmailForm').show();
	})
});

$(document).ready(function(){
    var next = 1;
    $(".add-more").click(function(e){
        e.preventDefault();
        var addto = "#field" + next;
        var addRemove = "#field" + (next);
        next = next + 1;
        var newIn = '<input style="width:300px" autocomplete="off" placeholder="Type something" class="input form-control" id="field' + next + '" name="field' + next + '" type="text">';
        var newInput = $(newIn);
        var removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >-</button></div><div id="field">';
        var removeButton = $(removeBtn);
        $(addto).after(newInput);
        $(addRemove).after(removeButton);
        $("#field" + next).attr('data-source',$(addto).attr('data-source'));
        $("#count").val(next);  
        
            $('.remove-me').click(function(e){
                e.preventDefault();
                var fieldNum = this.id.charAt(this.id.length-1);
                var fieldID = "#field" + fieldNum;
                $(this).remove();
                $(fieldID).remove();
            });
    });
    

    
});

