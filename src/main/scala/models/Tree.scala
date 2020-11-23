package models

sealed trait Tree {

  def setChild(left: Tree, right: Tree): Tree

}

object Tree {

  case object Empty extends Tree {

    override def setChild(left: Tree, right: Tree): Tree = Node("Empty", left, right)

  }

  case class Node(value: String, left: Tree = Empty, right: Tree = Empty) extends  Tree {

    //modify the tree so that low-priority operations are performed later
    override def setChild(left: Tree, right: Tree): Tree =
      right match {
        case low: LowPriorityNode =>
          val modified = copy(left = left, right = low.left)
          low.copy(left = modified)
        case _ =>
          copy(left = left, right = right)
      }

  }

  case class LowPriorityNode(value: String, left: Tree = Empty, right: Tree = Empty) extends Tree {

    override def setChild(left: Tree, right: Tree): Tree = copy(left = left, right = right)

  }

  case class Leaf(value: String) extends Tree {

    override def setChild(left: Tree, right: Tree): Tree = Node(value, left, right)

  }

}