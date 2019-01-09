package cn.edu.heuet.II.algorithm;

//定义一个单链表   
class Node {
	// 变量
	private int record;
	// 指向下一个对象
	private Node nextNode;

	public Node(int record) {
		this.record = record;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public Node getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
}

/**
 * 两种方式实现单链表的反转(递归、普通)
 */
public class RevSingleLinkFactory {
	/**
	 * 递归，在反转当前节点之前先反转后续节点
	 */
	// 最终传递一个指向最后一个节点的变量
	public static Node reverse1(Node head) {
		// 当为空或者本节点为末尾节点的时候
		if (head == null || head.getNextNode() == null)
			return head;
		Node reversedHead = reverse1(head.getNextNode());
		// 获取先前的下一个节点，让该节点指向自身
		head.getNextNode().setNextNode(head);
		// 破坏以前自己指向下一个节点
		head.setNextNode(null);
		// 层层传递给最上面的
		return reversedHead;
	}

	/**
	 * 遍历，将当前节点的下一个节点缓存后更改当前节点指针
	 */
	public static Node reverse2(Node head) {
		if (null == head) {
			return head;
		}
		Node pre = head;
		Node cur = head.getNextNode();
		Node next;

		while (cur != null) {
			// 断之前先找到原始的下一个节点
			next = cur.getNextNode();
			// 逆序连接
			cur.setNextNode(pre);
			// 两个节点同时滑动
			pre = cur;
			cur = next;
		}
		// 将原链表的头节点的下一个节点置为null，再将反转后的头节点赋给head
		head.setNextNode(null);
		head = pre;
		return head;
	}

	public static void main(String[] args) {
		// 带有头结点
		Node head = new Node(0);
		Node tmp = null; // 保存临时变量
		Node cur = null; // 始终指向末尾节点
		// 构造一个长度为10的链表，保存头节点对象head
		// 利用尾插入法
		for (int i = 1; i < 10; i++) {
			tmp = new Node(i);
			if (1 == i) {
				head.setNextNode(tmp);
			} else {
				cur.setNextNode(tmp);
			}
			cur = tmp;
		}
		// 打印反转前的链表
		Node h = head;
		while (h != null) {
			System.out.print(h.getRecord() + " ");
			h = h.getNextNode();
		}
		// 调用反转方法
		head = reverse1(head);
		System.out.println("\n*******************");
		// 打印反转后的结果
		while (head != null) {
			System.out.print(head.getRecord() + " ");
			head = head.getNextNode();
		}
	}
}