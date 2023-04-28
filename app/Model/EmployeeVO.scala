package Model

import slick.jdbc.MySQLProfile.api._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsObject, JsPath, Json, Reads, Writes}

case class EmployeeVO(employeeId: Option[Int],company: String,fullName: String,department: String,email: String,salary: Int)

class EmployeeTableDef(tag: Tag) extends Table[EmployeeVO](tag,"record_Employee"){

  def employeeId = column[Option[Int]]("employeeID",O.PrimaryKey,O.AutoInc)
  def company = column[String]("company")
  def fullName = column[String]("fullName")
  def department = column[String]("department")
  def email = column[String]("email")
  def salary = column[Int]("salary")

  override def * =
    (employeeId,company,fullName,department,email,salary) <>  ( (EmployeeVO.apply _).tupled,EmployeeVO.unapply )

}

object EmployeeVO{

  implicit val writes: Writes[EmployeeVO] = new Writes[EmployeeVO] {
    override def writes(o: EmployeeVO): JsObject = Json.obj(
      "employeeId" -> o.employeeId,
      "company" -> o.company,
      "fullName" -> o.fullName,
      "department" -> o.department,
      "email" -> o.email,
      "salary" -> o.salary
    )
  }

  implicit val reads: Reads[EmployeeVO] = (
    (JsPath \ "employeeId").readNullable[Int] and
      (JsPath \ "company").read[String] and
      (JsPath \ "fullName").read[String] and
      (JsPath \ "department").read[String] and
      (JsPath \ "email").read[String] and

      (JsPath \ "salary").read[Int])(EmployeeVO.apply _)

}
