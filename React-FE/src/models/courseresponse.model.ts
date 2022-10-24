export interface CourseResponseModel {
  id?: number;
  title: string;
  description: string;
  url: string;
  authorId?: number;
  studentIds?: number[];
}
