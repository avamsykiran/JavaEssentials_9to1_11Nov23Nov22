<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="com.cts.incomestatement.entities.TxnType" %>

<html>
	<head>
		<title>Income Statement - New Transaction Page</title>
	</head>
	<body>
		<h3>Income Statement</h3>
		<hr />
		<nav>
			<a href="/home">Home</a>
		</nav>
		<hr />
		<form:form method="POST" modelAttribute="txn" style="width:40%;margin:auto;border:1px solid black;padding:10px">
			<div>
				<form:label path="header">Header</form:label>
				<form:input path="header"/>				
			</div>
			<div>
				<form:label path="amount">Amount</form:label>
				<form:input path="amount" type="number"/>				
			</div>
			<div>
				<form:label path="txnDate">Date Of Transaction</form:label>
				<form:input path="txnDate" type="date"/>				
			</div>
			<div>
				<form:label path="type">Type of Transaction</form:label>
				<form:select path="type">
					<form:options items="${TxnType.values()}"/>
				</form:select>
			</div>
			<div style="text-align:right">
				<button>ADD</button>				
			</div>
		</form:form>
	</body>
</html>