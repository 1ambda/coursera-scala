(* https://class.coursera.org/proglang-003 *)

fun pow (x: int, y: int) =
    if y = 0 then 1 else x * pow(x, y - 1);

fun cube (x: int) =
    pow(x, 3);
				
fun sum_list (xs: int list) =
    if null xs
    then 0
    else hd xs + sum_list(tl xs)

fun product_list (xs: int list) =
    if null xs
    then 1
    else hd xs * product_list(tl xs)

fun countdown (x: int) =
    if x = 0
    then []
    else x :: countdown(x - 1)

fun append (xs: int list, ys: int list) =
    if null xs
    then ys
    else (hd xs) :: append(tl xs, ys)
	    
fun sum_pair_list (xs: (int * int) list) =
    if null xs
    then 0
    else (#1 (hd xs)) + (#2 (hd xs)) + sum_pair_list(tl xs)

fun firsts (xs: (int * int) list) =
    if null xs
    then []
    else #1 (hd xs) :: firsts(tl xs)
    
fun seconds (xs: (int * int) list) =
    if null xs
    then []
    else #2 (hd xs) :: seconds(tl xs)

fun sum_pair_list (xs: (int * int) list) =
    if null xs
    then 0
    else sum_list(firsts xs) + sum_list(seconds xs)

fun factorial (n : int) =
    product_list(countdown(n))

fun silly () =
    let
	val x = 3
    in
	(let val x = 2 in x + 1 end) + (let val y = x + 1 in y + 1 end)
    end
		    
fun count_from_1 (x: int) =
    let
	fun count (from: int) =
	    if from = x
	    then []
	    else from :: count(from + 1)
    in
	count(1)
    end
		     
fun good_max (xs: int list) =
    if null xs then 0
    else if null (tl xs) then hd xs
    else
	let
	    val res = good_max(tl xs)
	in
	    if hd xs > res then hd xs
	    else res
		    
	end

fun max1 (xs: int list) =
    if null xs then NONE
    else
	let val res = max1(tl xs)
	in
	    if isSome res andalso valOf res > hd xs then res
	    else SOME (hd xs)
	end

fun max2 (xs: int list) =
    if null xs then NONE
    else
	let
	    fun max_non_empty(ys: int list) =
		if null (tl ys) then hd ys
		else
		    let val res = max_non_empty(tl ys)
		    in if hd xs > res then hd ys
		       else res
		    end
	in
	    SOME (max_non_empty xs)
	end
	    
		
