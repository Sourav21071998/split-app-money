function addData(){
	console.log("Inside add Details");
	let token = localStorage.getItem('token');

	const username = document.getElementById('username').value; 
	const expenses = document.getElementById('expenses').value;
	const expenseName = document.getElementById('expenseName').value;

	const expenseData = {
		username: username,
		expenses: expenses,
		expenseName: expenseName
	};

	fetch('http://localhost:8080/expenses/add', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			//'Accept': 'application/json', 
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Credentials': 'true',
			Authorization: 'Bearer '+token
		},
		body: JSON.stringify(expenseData)
	})
	.then(response => response.json())
	.then(data => {
		console.log(data)
		if (data.status == 'CREATED') {

			//localStorage.setItem('token', data.token); 
			document.getElementById('message').innerText = 'Expenses saved successfully for username: '+username;
			document.getElementById('message').style.color = 'green';
		}else{
			document.getElementById('message').innerText = data.message;
		}
	})
	.catch(error => {
		console.log('Error:',error);
		document.getElementById('message').innerText = 'Bad Request. Expenses needs to be in number format';
		document.getElementById('message').style.color = 'red';
	});
}
