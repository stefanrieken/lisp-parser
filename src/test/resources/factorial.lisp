(define fact (method (n)
	(if (= n 0)
		1
		(* n (fact (- n 1)))
	)
))
(println (fact 3))