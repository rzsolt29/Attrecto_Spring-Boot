import { UserModel } from './user.model';

export interface CourseModel {
  id?: number;
  title: string;
  description: string;
  url: string;
  author?: UserModel;
  students?: UserModel[];
}
