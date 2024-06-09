document.getElementById('registerForm').addEventListener('submit',function(event) {

	event.preventDefault();

	const email = document.getElementById('email').value;
	const password = document.getElementById('password').value;

	const registerData = {
		email: email,
		password: password
	};

	console.log(registerData)

	fetch('http://localhost:8080/register', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(registerData)
		})
		.then(response => response.json())
		.then (data => {
			console.log(data)
			if (data.status == 'CREATED'){
				document.getElementById('message').innerText = 'Registration Successful! Login to continue.';
				document.getElementById('message').style.color = 'green';
				//window.location.href='addExpenses.html'
		}else{
			document.getElementById('message').innerText = 'Login failed: '+(data.message || 'Invalid credentials');
		}
		})
		.catch(error => {
			console.error('Error:',error);
			document.getElementById('message').innerText = 'Login failed: Network error';
		});
});