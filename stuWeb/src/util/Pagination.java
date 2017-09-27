package util;

public class Pagination {
	private int yeNum;// 一页信息条数
	private int begin;// 开始页
	private int end;// 结束页
	private int ye;
	private int maxYe;
	private int yeMa;// 显示几个页码，例：1 2 3 4 5

	public Pagination(int ye, int max, int yeNum, int yeMa) {
		this.ye = ye;
		this.yeNum = yeNum;
		this.yeMa = yeMa;
		cal(max);
	}

	public void cal(int max) {
		// 保证最小页是第一页
		if (ye < 1) {
			ye = 1;
		}
		// 根据最大记录数，求出最大页码数
		this.maxYe = max % yeNum == 0 ? max / yeNum : max / yeNum + 1;
		// 保证最大页是最后一页
		if (ye > maxYe) {
			ye = maxYe;
		}

		// 换算开始页和结束页// 让当前页页码显示在中间
		begin = ye - yeMa / 2;
		end = ye + yeMa / 2;
		// 控制页码是1,2的情况
		if (begin < 1) {
			begin = 1;
			end = yeMa;
		}
		// 控制页码是最后两页的情况
		if (end > maxYe) {
			end = maxYe;
			begin = end - yeMa + 1;
		}
		// 控制最大页，不足5的情况
		if (maxYe < yeMa) {
			begin = 1;
			end = maxYe;
		}
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getYe() {
		return ye;
	}

	public void setYe(int ye) {
		this.ye = ye;
	}

	public int getMaxYe() {
		return maxYe;
	}

	public void setMaxYe(int maxYe) {
		this.maxYe = maxYe;
	}
}
