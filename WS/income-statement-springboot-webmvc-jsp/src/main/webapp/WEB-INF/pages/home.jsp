<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="com.cts.incomestatement.entities.TxnType" %>

<html>
	<head>
		<title>Income Statement - home Page</title>
	</head>
	<body>
		<h3>Income Statement</h3>
		<hr />
		<nav>
			<a href="/add">New Txn</a>
		</nav>
		<hr />
		
		<c:choose>
			<c:when test="${txns.isEmpty() }">
				<p><strong>No transaction to display</strong></p>
			</c:when>
			<c:otherwise>
				<table style="width:60%;margin:auto;border:1px solid black;">
					<thead>
						<tr>
							<th>Txn#</th>
							<th>Txn Date</th>
							<th>Header</th>
							<th>Credit</th>
							<th>Debit</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="t" items="${txns }">
							<tr>
								<td style="text-align:right">${t.txnId }</td>
								<td>${t.txnDate }</td>
								<td>${t.header }</td>
								<td style="text-align:right">
									${t.type==TxnType.CREDIT?t.amount+"":"" }
								</td>
								<td style="text-align:right">
									${t.type==TxnType.DEBIT?t.amount+"":"" }
								</td>
							</tr>
						</c:forEach>
					</tbody>				
				</table>
			</c:otherwise>
		</c:choose>
	</body>
</html>