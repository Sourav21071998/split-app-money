document.getElementById('loginForm').addEventListener('submit',function(event) {

	event.preventDefault();

	const email = document.getElementById('email').value;
	const password = document.getElementById('password').value;

	const loginData = {
		email: email,
		password: password
	};

	console.log(loginData)

	fetch('http://localhost:8080/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(loginData)
		})
		.then(response => response.json())
		.then (data => {
			console.log('Before data.status check');
			console.log(data)
			if (data.status == 'OK'){

				localStorage.setItem('token', data.message); 
				document.getElementById('message').innerText = 'Login Successful!';
				document.getElementById('message').style.color = 'green';
				window.location.href='addExpenses.html'

		}else{
			document.getElementById('message').innerText = 'Login failed: '+(data.message || 'Invalid credentials');
		}
		})
		.catch(error => {
			console.log('Error:',error);
			document.getElementById('message').innerText = 'Login failed: Network error';
		});
});