import { CourseModel } from './course.model';

export interface UserModel {
  id: number;
  email: string;
  name: string;
  role?: string;
  password?: string;
  courses?: CourseModel[];
}
