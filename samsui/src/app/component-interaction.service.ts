import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComponentInteractionService {

  componentTypeMessage: Subject<COMPONENT_TYPE_MESSAGE> = new Subject<COMPONENT_TYPE_MESSAGE>();
  constructor() { }
}
export enum COMPONENT_TYPE_MESSAGE {
  SUBMITTER_VIEW_INITIALIZE,
  NOTIFICATION_VIEW_INITIALIZE,
  SUBMITTER_TAB_SUBMIT,
  SUBMITTER_TAB_VIEW,
  SUBMITTER_TAB_NOTIFICATION,
}
