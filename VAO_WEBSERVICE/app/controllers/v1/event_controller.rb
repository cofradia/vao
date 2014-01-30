class V1::EventController < ApplicationController
  

  def create 
    event = Event.create(params[:event])
  end    

  def index
    update_time = params[:update_time]
    events = Event.where("created_at >= ? or update_at >= ? ", update_time)
  end

  def show
    event_id = params[:event_id]
    event = Event.find(event_id)
  end  

end
