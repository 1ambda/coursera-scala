package coursera.chapter1

object Generators {

  trait Generator[+T] {
    self => // an alias for "this"
    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] =
      new Generator[S] {
        def generate = f(self.generate).generate
      }
  }

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  val booleans = for (x <- integers) yield x > 0
  val pairs = for(x <- integers; y <- integers) yield (x, y)

  // before define map, flatMap
  // val booleans = new Generator[Boolean] {
  //   def generate = integers.generate > 0
  // }
  // val pairs = new Generator[(Int, Int)] {
  //   def generate = (integers.generate, integers.generate)
  // }

  def single[T](x: T): Generator[T] = new Generator[T] {
    def generator = x
  }

  def choose(l: Int, h: Int): Generator[Int] =
    for (x <- integers) yield l + x % (h - l)

  def oneOf[T](xs: T*): Generator[T] =
    for (i <- choose(0, xs.length)) yield xs(i)

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list

  def emptyLists = single(Nil)

  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  // tree generator
  trait Tree
  case class Leaf(x: Int)                   extends Tree
  case class Inner(left: Tree, right: Tree) extends Tree

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  // random testing
  def randomTest[T](g: Generator[T], times: Int = 100)(f: T => Boolean): Unit = {
    for (i <- 0 until times) {
      val value = g.generate
      assert(f(value), "test failed for" + value)
    }
    println("passed " + times + "tests")
  }
}
