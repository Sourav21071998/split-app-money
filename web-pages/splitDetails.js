function splitDetails() {
	console.log("split details");
	let token = localStorage.getItem('token');

	fetch('http://localhost:8080/expenses/split', {
		method: 'GET',
		headers: {
		'Content-Type': 'application/json',
		//'Accept': 'application/json',
		'Access-Control-Allow-Origin': '*',
		'Access-Control-Allow-Credentials': 'true',
		Authorization: 'Bearer '+token
		}
	})
	.then(response => response.json())
	.then(data => {
		console.log(data)
		if (data.status == 'OK') {
			document.getElementById('message').innerText = data.data.message;
			document.getElementById('message').style.color = 'green';
	}else{
		document.getElementById('message').innerText = 'Exception: '+(data.message || 'Invalid credentials');
	}
	})
	.catch(error => {

		console.error('Error:',error);
		document.getElementById('message').innerText = 'Exception: Network error';
	});
}