(define greeting "Hello, ")
(define hello (who) (print greeting who "!"))
; asdf
(
	(define resolvewho "me") ; technisch gesproken is dit een closure. Voor een 'echte' moeten we return values implementeren
	(hello resolvewho)
)
