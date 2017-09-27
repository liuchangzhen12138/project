package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Pagination;
import dao.StudentDao;
import entity.Student;

public class StudentServlet extends HttpServlet {
	StudentDao stuDao = new StudentDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = request.getParameter("type");
		if (type == null) {
			show(request, response);
		} else if (type.equals("showAdd")) {
			showAdd(request, response);
		} else if (type.equals("add")) {
			add(request, response);
		} else if (type.equals("showModify")) {
			showModify(request, response);
		} else if (type.equals("showModify2")) {
			showModify2(request, response);
		} else if (type.equals("modify")) {
			modify(request, response);
		} else if (type.equals("modify2")) {
			modify2(request, response);
		} else if (type.equals("delete")) {
			delete(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameter("selectId").split(",");
		boolean flag = false;
		for (int i = 0; i < ids.length; i++) {
			flag = stuDao.delete(Integer.parseInt(ids[i]));
			if (flag == false) {
				break;
			}
		}
		if (flag) {
			try {
				response.sendRedirect("stu");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));

			Student stu = new Student();
			stu.setId(id);
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			boolean flag = stuDao.modify(stu);

			if (flag) {

				response.sendRedirect("stu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modify2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String[] stuStrs = request.getParameter("stus").split(";");

			List<Student> list = new ArrayList<Student>();
			for (int i = 0; i < stuStrs.length; i++) {
				String[] stuStr = stuStrs[i].split(",");
				int id = Integer.parseInt(stuStr[0]);
				String name = stuStr[1];
				String sex = stuStr[2];
				int age = Integer.parseInt(stuStr[3]);
				Student stu = new Student();
				stu.setId(id);
				stu.setName(name);
				stu.setSex(sex);
				stu.setAge(age);
				list.add(stu);
			}

			boolean flag = stuDao.modify2(list);

			if (flag) {

				response.sendRedirect("stu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showModify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String[] ids = request.getParameter("selectId").split(",");
			Student stu = stuDao.searchById(Integer.parseInt(ids[0]));

			request.setAttribute("stu", stu);
			request.getRequestDispatcher("WEB-INF/student/modify.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showModify2(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String ids = request.getParameter("selectId");
			List<Student> list= stuDao.searchByIds(ids);

			request.setAttribute("stus", list);
			request.getRequestDispatcher("WEB-INF/student/modify2.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {

			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));

			Student stu = new Student();
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			boolean flag = stuDao.add(stu);

			if (flag) {
				response.sendRedirect("stu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showAdd(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/student/add.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) {
		try {

			int yeNum = 2;// 一页信息条数
			int yeMa = 5;// // 显示几个页码，例：1 2 3 4 5
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			int max = stuDao.searchCount();
			Pagination pagination = new Pagination(ye, max, yeNum, yeMa);
			ye = pagination.getYe();
			int begin = (ye - 1) * yeNum;
			List<Student> list = stuDao.searchByBegin(begin, yeNum);
			request.setAttribute("stus", list);
			request.setAttribute("p", pagination);
			request.getRequestDispatcher("WEB-INF/student/list.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
