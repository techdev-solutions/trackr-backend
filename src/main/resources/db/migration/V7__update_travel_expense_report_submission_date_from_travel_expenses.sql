UPDATE TravelExpenseReport ter
	SET submissionDate = (
		SELECT MAX(te.submissionDate)
		FROM TravelExpense te
		WHERE te.report_id = ter.id
	)
WHERE ter.submissionDate IS NULL;
