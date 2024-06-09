function queryString(url) {
	const strl = url.split('?')[1];
	const params = {};

	if (strl) {
		const pairs = strl.split('&');
		for (const pair of pairs) {
			const [key, value] = pair.split('=');
			params[key] = decodeURIComponent(value.replace(/\+/g, ' '));
		}
	}
	return params;
}


function modifyDetails() {
	let token = localStorage.getItem('token'); 
	console.log(token);
	const result = queryString (window.location.href); 
	console.log(result.id);
	const baseUrl='http://localhost:8080/expenses/modify'; 
	const params = { id: result.id };
	const query = new URLSearchParams(params).toString();
	console.log("Query=",query);
const urlWithParams = `${baseUrl}?${query}`;
	console.log(urlWithParams);

	const username = document.getElementById('username').value;
	const expenseName = document.getElementById('expenseName').value;
	const expenses = document.getElementById('expenses').value;

	const expenseData = {
	username: username,
	expenseName: expenseName,
	expenses: expenses
	};
	
	console.log(expenseData);
	
	fetch (urlWithParams, {
	method: 'PUT',
	headers: {
		'Content-Type': 'application/json',
		//'Accept': 'application/json',
		'Access-Control-Allow-Origin': '*',
		'Access-Control-Allow-Credentials': 'true',
		Authorization: 'Bearer '+token
		},
		body: JSON.stringify(expenseData)
	})
	.then(response => response.json())
	.then(data => {
	console.log(data)
	if(data.status == 'OK') {
		document.getElementById('message').innerText = 'Datà modified successfully';
		document.getElementById('message').style.color ='green';
	}else{
		document.getElementById('message').innerText = 'Data Modification failed: '+ (data.message || 'Invalid credentials');

	}
	})
	.catch(error => {
	console.error('Error:', error);
	document.getElementById('message').innerText = 'Data Modification failed: Network error';
	});
}